(ns qiaobutang.reviewer.task.js
  (:require [qiaobutang.reviewer.util :as util]))

(defn review-line [n line lines]
  (if (> (count line) 1024)
    []
    [(util/review-void-element line)]))

(defn run [path]
  (util/review-by-line path "js" review-line))
