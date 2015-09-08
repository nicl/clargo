(ns clargo.core-test
  (:require [clojure.test :refer :all]
            [clargo.core :refer :all]))

(def valid-error {:uri "https://example.com"
                  :errorKey "1"
                  :errorMessage "Something is wrong"})

(def valid-success {:uri "https://example.com"
                    :data []
                    :links [{:rel "prev" :href "https://example.com?page=1"}
                            {:rel "next" :href "https://example.com?page=3"}]})

(def invalid-error (dissoc valid-error :uri))

(def invalid-success (dissoc valid-success :data))

(defn pass? [check] (not check))

(defn fail? [check] (and (map? check) (not-empty check)))

(deftest argo-test
  (testing "Valid Argo responses accepted."
    (is (pass? (argo? valid-success)))
    (is (pass? (argo? valid-error))))
  (testing "Invalid Argo responses rejected."
    (is (fail? (argo? invalid-success)))
    (is (fail? (argo? invalid-error)))))
