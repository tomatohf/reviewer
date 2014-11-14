(ns qiaobutang.reviewer.main
  (:require [clojure.tools.namespace.find :as ns]
            [clojure.java.classpath :as cp]
            clansi
            [clojure.string :as string]))

(defn task-ns []
  (filter #(.startsWith (str %) "qiaobutang.reviewer.task.")
          (ns/find-namespaces (cp/classpath))))

(defn task [ns]
  (require ns)
  (ns-resolve ns 'run))

(defmacro print-nested-result [result empty-seq rest-empty-var vars & body]
 `(loop [[~vars & rest-result#] ~result]
    (let [rest-empty# (empty? rest-result#)
          ~rest-empty-var rest-empty#]
      (doseq [empty# ~empty-seq]
        (print (if empty# " " "│"))
        (print "   "))
      (print (if rest-empty# "└" "├"))
      (print "── ")
      ~@body
      (if-not rest-empty# (recur rest-result#)))))

(defn print-result [ns result]
  (when (seq result)
    (println (clansi/style ns :bg-blue))
    (print-nested-result result
                         []
                         rest-files-empty
                         [file file-result]
      (println (clansi/style (.getCanonicalPath file) :red))
      (print-nested-result file-result
                           [rest-files-empty]
                           rest-lines-empty
                           [n line line-result]
        (println (clansi/style (str "#" n) :red) (clansi/style (string/trim line) :magenta))
        (print-nested-result line-result
                           [rest-files-empty rest-lines-empty]
                           _
                           problem
          (println (clansi/style problem :yellow)))))))

(defn -main
  ([] (-main "."))
  ([path & _]
    (doseq [ns (task-ns)]
      (when-let [f (task ns)]
        (print-result ns (f path))))))
