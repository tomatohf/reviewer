(ns qiaobutang.reviewer.util
  (:require [me.raynes.fs :as fs]
            [clojure.java.io :as io]))

(defn files [dir extension]
  (fs/find-files dir (re-pattern (str ".+\\." extension))))

(defn lines [file]
  (with-open [rdr (io/reader file)]
    (doall (line-seq rdr))))

(defmacro not-empty-let [bindings & body]
  (let [[name coll] bindings]
   `(let [~name (filter (complement nil?) ~coll)]
      (when (seq ~name)
        ~@body))))

(defmacro not-empty-with [coll & with]
 `(not-empty-let [name# ~coll]
    (if (seq '~with) [~@with name#] name#)))

(defn review-by-line [path extension f]
  (let [review-line
        (fn [n line lines]
          (not-empty-with (f n line lines)
                          (+ n 1) line))
        review-file
        (fn [file]
          (let [lines (vec (lines file))]
            (not-empty-with (map-indexed #(review-line %1 %2 lines) lines)
                            file)))]
    (not-empty-with (map review-file (files path extension)))))

(def void-elements
  '#{area base br col command embed hr img input keygen link meta param source track wbr})

(defn contain-void-element? [s]
  (some #(.contains s (str "</" % ">")) void-elements))

(defn review-void-element [s]
  (if (contain-void-element? s)
    "end tags must not be specified for void elements"
    nil))
