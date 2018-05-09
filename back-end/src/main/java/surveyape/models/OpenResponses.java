package surveyape.models;

public class OpenResponses {

        private String response;

        private String optionid;

        private String type;

        private String questionid;

        public String getResponse ()
        {
            return response;
        }

        public void setResponse (String response)
        {
            this.response = response;
        }

        public String getOptionid ()
        {
            return optionid;
        }

        public void setOptionid (String optionid)
        {
            this.optionid = optionid;
        }

        public String getType ()
        {
            return type;
        }

        public void setType (String type)
        {
            this.type = type;
        }

        public String getQuestionid ()
        {
            return questionid;
        }

        public void setQuestionid (String questionid)
        {
            this.questionid = questionid;
        }

        @Override
        public String toString()
        {
            return "ClassPojo [response = "+response+", optionid = "+optionid+", type = "+type+", questionid = "+questionid+"]";
        }
}
