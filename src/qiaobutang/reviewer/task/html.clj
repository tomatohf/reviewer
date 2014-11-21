(ns qiaobutang.reviewer.task.html
  (:require [qiaobutang.reviewer.util :as util]))

(defn review-line [n line lines]
  [(util/review-void-element line)])

(defn run [path]
  (util/review-by-line path "html" review-line))
