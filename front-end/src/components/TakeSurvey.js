import React, { Component } from 'react';
import '../styles/SurveyContainer.css';
import queryString          from 'query-string';
import EmailInput from './EmailInput';
import OpenSurveyQuestions from './OpenSurveyQuestions';
import SurveyQuestions from './SurveyQuestions';
import UniqueEmailTracking from './UniqueEmailTracking';
class TakeSurvey extends Component{
    constructor(props){
        super(props);
        // let x = this.props.selectedSurvey;
        this.state={
            "type":'',
            "surveyid": '',
            "callComponent": ''
        }
    }

    componentWillMount(){
        let parsed = queryString.parse(this.props.urlData.location.search);
        let type = this.props.type;
        if(type === 'close'){
            if(localStorage.getItem("userEmail") !== null && localStorage.getItem("userEmail") !== ""
                    && localStorage.getItem("userEmail") !== undefined){
                let surveyData = {
                    "type":type,
                    "surveyid": parsed.surveyid,
                    "email":localStorage.getItem("userEmail")
                }
                this.setState({
                    ...this.state,
                    "type":type,
                    "surveyid": parsed.surveyid,
                    "callComponent": <div> <SurveyQuestions surveyData={surveyData}/> </div>
                });
            } else{
                this.setState({
                    ...this.state,
                    "type":type,
                    "surveyid": parsed.surveyid,
                    "callComponent": <div> <EmailInput surveyid={parsed.surveyid} stype={type}/> </div>
                });
            }
        } else if(type === 'unique'){
            this.setState({
                ...this.state,
                "type":type,
                "surveyid": parsed.surveyid,
                "callComponent": <div> <UniqueEmailTracking surveyid={parsed.surveyid} stype={type}/> </div>
            });
        } else if(type === 'general'){
            this.setState({
                ...this.state,
                "type":type,
                "surveyid": parsed.surveyid,
                "callComponent": <div> <OpenSurveyQuestions surveyid={parsed.surveyid}/> </div>
            });
        } else{
            alert("Wrong URL");
        }
    }

    render(){

        return (
            <div>
                {this.state.callComponent}
                {/*<EmailInput/>*/}
            </div>
        )
    }
}
export default TakeSurvey;