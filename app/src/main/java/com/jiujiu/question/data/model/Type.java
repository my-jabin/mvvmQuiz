package com.jiujiu.question.data.model;

public enum  Type {
    Any,
    MultipleChoice{
        @Override
        public String toString() {
            return "Multiple Choice";
        }
    },
    TrueFalse{
        @Override
        public String toString() {
            return "True/False";
        }
    }
}
