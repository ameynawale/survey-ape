import React, {Component} from 'react';
import { Route, withRouter } from 'react-router-dom';
import Samplesurveypage from './samplesurveypage';
import SurveyListing from './SurveyListing';
import HomeHeader from "./HomeHeader";
import SignIn from "./SignIn";
import SignUp from './SignUp';
import Header from './Header';
import * as API from '../api/API';

class CustomRouter extends Component {

    constructor(props) {
        super(props);
        this.state = {
            surveyName: '',
            surveyId: '',
            createdbyme: [],
            sharedwithme: []
        };
    }


    handleAddSurvey = (surveydata) => {
        var Survey = {
            surveyname: surveydata.surveyname,
            surveytype: surveydata.surveytype,
            validity: surveydata.validity
        }
        console.log('inside handleAddSurvey');

        API.addSurvey(Survey)
            .then((res) => {
                if(res.status === 201)
                {
                    this.setState({
                        surveyName: res.surveyName,
                        surveyId: res.surveyId
                    });
                    this.props.history.push("/CreateSurvey");
                }
            })
    }

    handleGetSurveyListing = () => {
        API.getSurveyListing()
            .then((res) => {
                if(res.status === 200)
                {
                    this.setState({
                        createdbyme: res.createdbyme,
                        sharedwithme: res.sharedwithme
                    });
                    this.props.history.push("/surveys");
                }
            })
    }

    render() {
        return (
            <div>
                <Route exact path="/" render={() => (
                    <div>
                        <HomeHeader/>
                        <SignIn history={this.props.history}/>
                        {/*<Samplesurveypage/>*/}
                    </div>
                )}/>
                <Route exact path="/signUp" render={() => (
                    <div>
                        <HomeHeader/>
                        <SignUp/>
                    </div>
                )}/>
                <Route exact path="/CreateSurvey" render={() => (
                    <div>
                        <Header handleAddSurvey={this.handleAddSurvey} handleGetSurveyListing={this.handleGetSurveyListing}/>
                        <Samplesurveypage surveydata={this.state}/>
                    </div>
                )}/>
                <Route exact path="/surveys" render={() => (
                    <div>
                        <Header handleAddSurvey={this.handleAddSurvey} handleGetSurveyListing={this.handleGetSurveyListing}/>
                        <SurveyListing surveydata={this.state}/>
                    </div>
                )}/>
            </div>
        );
    }
}

export default withRouter(CustomRouter);