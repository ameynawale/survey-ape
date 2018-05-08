import React, { Component } from 'react';
import '../styles/SurveyContainer.css';
import queryString          from 'query-string';
import EmailInput from './EmailInput';
import OpenSurveyQuestions from './OpenSurveyQuestions';
import SurveyQuestions from './SurveyQuestions';
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
        if(type === 'unique'){
            this.setState({
                ...this.state,
                "type":type,
                "surveyid": parsed.surveyid,
                "callComponent": <div> <EmailInput surveyid={parsed.surveyid}/> </div>
            });
        } else if(type === 'close'){
            this.setState({
                ...this.state,
                "type":type,
                "surveyid": parsed.surveyid,
                "callComponent": <div> <EmailInput surveyid={parsed.surveyid}/> </div>
            });
        } else{
            this.setState({
                ...this.state,
                "type":type,
                "surveyid": parsed.surveyid,
                "callComponent": <div> <OpenSurveyQuestions surveyid={parsed.surveyid}/> </div>
            });
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