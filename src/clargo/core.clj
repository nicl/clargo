(ns clargo.core
  (:require [schema.core :as s]))

(def Link
  "A schema for an Argo link"
  {:rel s/Str
   :href s/Str})

(def Success
  "A schema for an Argo success response"
  {:uri s/Str
   :data s/Any
   (s/optional-key :links) [Link]})

(def Err
  "A schema for an Argo error response"
  {:uri s/Str
   :errorKey s/Str
   :errorMessage s/Str
   (s/optional-key :data) s/Any
   (s/optional-key :links) [Link]})

(def Argo
  "A schema for an Argo response of any kind"
  (s/conditional :errorKey Err :else Success))

(defn argo?
  "Checks whether data is a valid Argo response"
  [data]
  (let [errors (s/check Argo data)]
    errors))
