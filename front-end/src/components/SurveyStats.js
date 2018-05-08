import React, { Component } from 'react';
import '../styles/SurveyContainer.css';
import FormContainer from "./FormContainer";
// import {Button, Modal} from 'react-bootstrap';
import { Table } from 'reactstrap';
import classnames from 'classnames';

class SurveyStats extends Component {
    constructor(props){
        super(props);
        let x = this.props.selectedSurvey;
        this.state={
            "surveyId": x.surveyId,
            "surveyName": x.surveyName,
            "statsOfSurvey":{}
        }
    }
    componentWillMount(){
        this.setState({
            ...this.state,
            statsOfSurvey: {
                "startTime": "2018-05-23 13:45:45",
                "endTime": "2018-05-30 13:45:45",
                "noOfParticipants": 30,
                "rate": "60%",
                "questions":[
                    {
                        "questionNo": 1,
                        "choices":[
                            {
                                "option": "a",
                                "number": 10
                            },
                            {
                                "option": "b",
                                "number": 20
                            },
                            {
                                "option": "c",
                                "number": 30
                            },
                            {
                                "option": "d",
                                "number": 14
                            }
                        ]
                    },
                    {
                        "questionNo": 2,
                        "choices":[
                            {
                                "option": "a",
                                "number": 10
                            },
                            {
                                "option": "b",
                                "number": 40
                            },
                            {
                                "option": "c",
                                "number": 56
                            }
                        ]
                    }
                ]
            }
    });

    }
    render(){
        return(
            <div className="survey-container">
                <div className="form-design-container">
                    <div className="form-container">
                        <h1>Stats for {this.state.surveyName}</h1>
                        <h3>General Metrics</h3>
                        <Table>
                            <thead>
                            <tr>
                                <th>Start Time</th>
                                <th>End Time</th>
                                <th>Number of Participants</th>
                                <th>Rate</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <td>{this.state.statsOfSurvey.startTime}</td>
                                <td>{this.state.statsOfSurvey.endTime}</td>
                                <td>{this.state.statsOfSurvey.noOfParticipants}</td>
                                <td>{this.state.statsOfSurvey.rate}</td>
                            </tr>
                            </tbody>
                        </Table>
                        <h3>Question Metrics</h3>
                        <Table>
                            <thead>
                            <tr>
                                <th>Question Number</th>
                                <th>Option A</th>
                                <th>Option B</th>
                                <th>Option C</th>
                                <th>Option D</th>
                            </tr>
                            </thead>
                            <tbody>
                            {/*{this.state.statsOfSurvey.questions}*/}
                            {/*{this.state.statsOfSurvey.questions.map((stats) =>*/}
                                {/*<td>{stats.questionNo}</td>)}*/}

                            {/*{this.stats.choices.map((options) =>*/}
                                    {/*<td>{options.number}</td>*/}
                                {/*)*/}
                            {/*}*/}
                            {
                                this.state.statsOfSurvey.questions.map((stat) =>(
                                <tr>
                                <td>{stat.questionNo}</td>
                                {stat.choices.map((x) => (
                                    <td>{x.number}</td>
                                ))}
                                </tr>
                                ))
                            }
                            </tbody>
                        </Table>
                    </div>
                </div>
            </div>
        )
    }
}

export default SurveyStats;