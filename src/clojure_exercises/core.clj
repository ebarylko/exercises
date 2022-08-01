(ns clojure-exercises.core
  (:import (java.net URLConnection URL)))


(defn- engine-search
  [url user-agent term]
  (println "Searching for " url)
  (try
    (let [target (format "%s?q=%s" url term)
          original url
          url (URL. target)
          hc (.openConnection url)]
      (.setRequestProperty hc "User-Agent" user-agent)
      (let [slp (slurp (.getContent hc))]
        (println "Done with" original)
        slp))
    (catch Exception e (println e) "This is an error" )))


(defn google-search
  [term]
  (engine-search
   "https://google.com/search"
   "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2"
   term))


(defn bing-search
  [term]
  (engine-search
   "https://bing.com/search"
   "Mozilla/5.0 AppleWebKit/537.36 (KHTML, like Gecko; compatible; bingbot/2.0; +http://www.bing.com/bingbot.htm) Chrome/100.0.4896.127 Safari/537.36"
   term))

(defn ddg-search
  [term]
  (engine-search
   "https://duckduckgo.com/"
   "Mozilla/5.0 (iPhone; CPU iPhone OS 15_3_1 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/15.3 Mobile/15E148 DuckDuckGo/7 Safari/605.1.15"
   term))

(defn find-term
  ([term] (find-term term google-search bing-search))
  ([term & browsers]
   (let [site (promise)]
     (doseq [browser browsers]
       (future (deliver site (browser term))))
     @site)))

(defn find-urls
  [html]
  (re-seq #"https?://[^<>\"']+" html))


(defn extract-urls
  [term & browsers]
  (let [futures (doall (map #(future (% term)) browsers))]
    (map (comp find-urls deref) futures)))
