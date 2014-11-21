(ns qiaobutang.reviewer.task.scala
  (:require [qiaobutang.reviewer.util :as util]))

(defn review-line [n line lines]
  (util/not-empty-with [(util/review-void-element line)]
                       (+ n 1) line))

(defn review-file [file]
  (let [lines (vec (util/lines file))]
    (util/not-empty-with (map-indexed #(review-line %1 %2 lines) lines)
                         file)))

(defn run [path]
  (util/not-empty-with (map review-file (util/files path "scala"))))
