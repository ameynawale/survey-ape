import React, {Component} from 'react';
import { Route, withRouter } from 'react-router-dom';
import Reacthomepage from './Reacthomepage';
import Samplesurveypage from './samplesurveypage';
import HomeHeader from "./HomeHeader";
import SignIn from "./SignIn";
import SignUp from './SignUp';

class CustomRouter extends Component {

    constructor(props) {
        super(props);
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
            </div>
        );
    }
}

export default withRouter(CustomRouter);