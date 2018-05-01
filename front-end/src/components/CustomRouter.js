import React, {Component} from 'react';
import { Route, withRouter } from 'react-router-dom';
import Reacthomepage from './Reacthomepage';
import Samplesurveypage from './samplesurveypage';
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
            surveyId: ''
        };
    }


    handleAddSurvey = (surveydata) => {
        API.addSurvey(surveydata)
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

    render() {
        return (
            <div>
                <Route exact path="/" render={() => (
                    <div>
                        <HomeHeader/>
                        <SignIn/>
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
                        <Header handleAddSurvey={this.handleAddSurvey}/>
                        <Samplesurveypage surveydata={this.state}/>
                    </div>
                )}/>
            </div>
        );
    }
}

export default withRouter(CustomRouter);