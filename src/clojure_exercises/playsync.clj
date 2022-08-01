(ns clojure-exercises.playsync
  (:require [clojure.core.async
             :as a
             :refer [>! <! >!! <!! go chan buffer close! thread
                     alts! alts!! timeout]]
            [clojure-exercises.core :as core]))

(defn find-term
  ([term]
   (find-term term core/google-search core/bing-search))
  ([term & browsers]
   (let [c (chan)]
     (doseq [browser browsers]
       (go (>! c (browser term))))
     (<!! c))))

(defn find-urls-checking [html]
  (let [found (core/find-urls html)]
    (if found
      found
      [:not-found-urls])))

(defn extract-urls
  [term & browsers]
  (let [html (chan 3 (map find-urls-checking)
                   (fn [e] (println "Error finding URLS" e)))
        #_#_input1 (a/to-chan! browsers)
        input (chan 3 nil (fn [_] (println "Input exception")))
     #_#_                       ssl (SSLEngine. )
        #_#_                    hand (.setUseClientMode ssl true)]
    (a/pipeline-async
     3
     html
     (fn [browser output] (go (>! output (browser term))))
     input)
   (a/go
     (loop [[b & rst] browsers]
       (when b
         (>! input b)
         (recur rst)))
     (println "Done with browsers")
     (a/close! input)
     (println "input closed:" (<! input)))
   (loop [[n port] (a/alts!! [html (a/timeout 5000)])
          cnt 0]
     (println "Loop count " cnt)
     (cond
       #_#_(= 20 cnt) (a/close! input)
       n (do
           (println "Reading html chan" (count n))
           (recur (a/alts!! [html (a/timeout 5000)])
                  (inc cnt)))))
   (println "Goodbye!")
    #_(<!! (a/into [] html))))



;; (find-term "indian+food")
;; (find-term "indian+food" core/google-search core/bing-search core/ddg-search)
(extract-urls "indian+food" core/google-search core/bing-search core/ddg-search)

;; (a/go-loop [[fst & rst] [core/google-search core/bing-search core/ddg-search]  results []]
;;   (if-not fst 
;;     results
;;     (recur rst
;;            (conj results (println (fst "indian+food"))))))

;; (go (println (core/bing-search "indian+food")))
;; (go (println (core/google-search "indian+food")))
;; (go (println (core/ddg-search "indian+food")))
;; (+ 2 2)
