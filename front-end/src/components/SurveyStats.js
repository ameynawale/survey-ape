import React, { Component } from 'react';
import '../styles/SurveyContainer.css';
import { Table } from 'reactstrap';
import * as API from '../api/API';
import {BarChart, Bar, XAxis, YAxis, CartesianGrid, Tooltip, Legend} from 'recharts';
const margin = {top: 20, right: 20, bottom: 30, left: 40};
const data = [
    {text: 'Man', value: 500},
    {text: 'Woman', value: 300}
];
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
        var payload = {
            "surveyId": x.surveyId
        }
        API.getStats(payload)
            .then(
                response => {
                    if(response.status === 200){
                        this.setState({
                            ...this.state,
                            statsOfSurvey: response.data
                        })
                    }
                },
                error => {
                    console.log(error.data.message);
                }
            );

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
                                "Participants": 10,
                                "percentage" : "20%"
                            },
                            {
                                "option": "b",
                                "Participants": 20,
                                "percentage" : "20%"
                            },
                            {
                                "option": "c",
                                "Participants": 30,
                                "percentage" : "20%"
                            },
                            {
                                "option": "d",
                                "Participants": 14,
                                "percentage" : "20%"
                            }
                        ]
                    },
                    {
                        "questionNo": 2,
                        "choices":[
                            {
                                "option": "a",
                                "Participants": 10
                            },
                            {
                                "option": "b",
                                "Participants": 40
                            },
                            {
                                "option": "c",
                                "Participants": 56
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
                            {
                                this.state.statsOfSurvey.questions.map((stat) =>(
                                <tr>
                                <td>{stat.questionNo}</td>
                                {stat.choices.map((x) => (
                                    <td>{x.percentage}</td>
                                ))}
                                </tr>
                                ))
                            }
                            </tbody>
                        </Table>
                        <h3>Graphs(Y axis shows the Number of participants and X axis is the options)</h3>
                        <row>
                            {
                                this.state.statsOfSurvey.questions.map((stat) =>(
                                    <div>
                                        <div className="displayQuestions">{stat.questionNo}</div>
                                        <BarChart width={700} height={400} data={stat.choices} margin={{top: 30, right: 30, left: 100, bottom: 30}}>
                                            <CartesianGrid/>
                                            <XAxis dataKey="option"/>
                                            <YAxis/>
                                            <Bar type="monotone" dataKey="Participants" fill="cornflowerblue" barSize={30}
                                                 />
                                        </BarChart>
                                    </div>
                                ))
                            }
                        </row>
                    </div>
                </div>
            </div>
        )
    }
}

export default SurveyStats;