import React, {Component} from 'react';
import { Route, withRouter } from 'react-router-dom';
import Reacthomepage from './Reacthomepage';
import Samplesurveypage from './samplesurveypage';

class CustomRouter extends Component {

    render() {
        return (
            <div>
                <Route exact path="/" render={() => (
                    <div>
                        <Samplesurveypage/>
                    </div>
                )}/>
            </div>
        );
    }
}

export default withRouter(CustomRouter);