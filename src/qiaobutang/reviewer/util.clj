(ns qiaobutang.reviewer.util
  (:require [me.raynes.fs :as fs]
            [clojure.java.io :as io]))

(defn files [dir extension]
  (fs/find-files dir (re-pattern (str ".+\\." extension))))

(defn lines [file]
  (with-open [rdr (io/reader file)]
    (doall (line-seq rdr))))
