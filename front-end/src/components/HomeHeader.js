import React, { Component } from 'react';
import '../styles/Header.css';
import { Route, withRouter } from 'react-router-dom';

class HomeHeader extends Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <div className="header-bar">
                {/*<button className="btn btn-primary survey-button"></button>*/}
                <span className="title">Survey-Ape</span>

                <button className="btn btn-primary share-button"
                        onClick={() => {
                            this.props.history.push("/");
                        }}>Sign In</button>
                <button className="btn btn-primary share-button"
                        onClick={() => {
                            this.props.history.push("/signUp");
                        }}>SignUp</button>
                <button className="btn btn-primary share-button"
                        onClick={() => {
                            this.props.history.push("/uniqueSurveys");
                        }}>View all Open Unique Survey</button>
            </div>
        );
    }
}

export default withRouter(HomeHeader);