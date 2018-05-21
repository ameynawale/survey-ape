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
            "surveyId": x.surveyid,
            "surveyName": x.surveyname,
            "statsOfSurvey":{},
            "questions":[]
        }
    }
    componentWillMount(){
        var payload = {
            "surveyid": this.state.surveyId
        }
        API.getStats(payload)
            .then(
                response => {
                    if(response.status === 200){
                        this.setState({
                            ...this.state,
                            statsOfSurvey: response.data,
                            questions: response.data.questions
                        })
                    }
                },
                error => {
                    console.log(error.data.message);
                }
            );

    //     this.setState({
    //         ...this.state,
    //         statsOfSurvey: {
    //             "startTime": "2018-05-20",
    //             "rate": 100,
    //             "questions": [
    //                 {
    //                     "questionid": 169,
    //                     "questiontype": "checkbox",
    //                     "question": "fav color",
    //                     "choices": [
    //                         {
    //                             "choiceDistribution": 3,
    //                             "choiceResponseRate": 100,
    //                             "option": "blue"
    //                         },
    //                         {
    //                             "choiceDistribution": 2,
    //                             "choiceResponseRate": 66.67,
    //                             "option": "yellow"
    //                         },
    //                         {
    //                             "choiceDistribution": 2,
    //                             "choiceResponseRate": 66.67,
    //                             "option": "black"
    //                         },
    //                         {
    //                             "choiceDistribution": 0,
    //                             "choiceResponseRate": 0,
    //                             "option": "red"
    //                         }
    //                     ]
    //                 },
    //                 {
    //                     "questionid": 167,
    //                     "questiontype": "radio",
    //                     "question": "gender",
    //                     "choices": [
    //                         {
    //                             "choiceDistribution": 1,
    //                             "choiceResponseRate": 33.33,
    //                             "option": "male"
    //                         },
    //                         {
    //                             "choiceDistribution": 0,
    //                             "choiceResponseRate": 0,
    //                             "option": "other"
    //                         },
    //                         {
    //                             "choiceDistribution": 2,
    //                             "choiceResponseRate": 66.67,
    //                             "option": "female"
    //                         }
    //                     ]
    //                 },
    //                 {
    //                     "questionid": 170,
    //                     "questiontype": "text",
    //                     "question": "name",
    //                     "choices": [
    //                         {
    //                             "textResponses": "Manali jain,I love baseball,mnnnbs s ds"
    //                         }
    //                     ]
    //                 },
    //                 {
    //                     "questionid": 172,
    //                     "questiontype": "date",
    //                     "question": "dob",
    //                     "choices": [
    //                         {
    //                             "textResponses": "2018-05-31,2018-05-28,2018-05-23"
    //                         }
    //                     ]
    //                 },
    //                 {
    //                     "questionid": 168,
    //                     "questiontype": "dropdown",
    //                     "question": "fav car",
    //                     "choices": [
    //                         {
    //                             "choiceDistribution": 2,
    //                             "choiceResponseRate": 66.67,
    //                             "option": "honda"
    //                         },
    //                         {
    //                             "choiceDistribution": 1,
    //                             "choiceResponseRate": 33.33,
    //                             "option": "toyota"
    //                         },
    //                         {
    //                             "choiceDistribution": 0,
    //                             "choiceResponseRate": 0,
    //                             "option": "BMW"
    //                         }
    //                     ]
    //                 },
    //                 {
    //                     "questionid": 171,
    //                     "questiontype": "rating",
    //                     "question": "java rating",
    //                     "choices": [
    //                         {
    //                             "choiceDistribution": 3,
    //                             "choiceResponseRate": 100,
    //                             "option": "5"
    //                         }
    //                     ]
    //                 }
    //             ],
    //             "noOfParticipants": 3,
    //             "endTime": "2018-06-20"
    //         }
    // });

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
                                <td>{this.state.statsOfSurvey.rate}%</td>
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
                            {/*{*/}
                                {/*this.state.questions.map((stat) =>(*/}
                                {/*<tr>*/}
                                {/*<td>{stat.question}</td>*/}
                                {/*{stat.choices.map((x) => (*/}
                                    {/*<td>{x.choiceResponseRate}%</td>*/}
                                {/*))}*/}
                                {/*</tr>*/}
                                {/*))*/}
                            {/*}*/}
                            </tbody>
                        </Table>
                        <h3>Graphs(Y axis shows the Number of participants and X axis is the options)</h3>
                        {/*<row>*/}
                            {/*{*/}
                                {/*this.state.statsOfSurvey.questions.map((stat) =>(*/}
                                    {/*<div>*/}
                                        {/*<div className="displayQuestions">{stat.question}</div>*/}
                                        {/*<BarChart width={700} height={400} data={stat.choices} margin={{top: 30, right: 30, left: 100, bottom: 30}}>*/}
                                            {/*<CartesianGrid/>*/}
                                            {/*<XAxis dataKey="option"/>*/}
                                            {/*<YAxis/>*/}
                                            {/*<Bar type="monotone" dataKey="choiceDistribution" fill="cornflowerblue" barSize={30}*/}
                                                 {/*/>*/}
                                        {/*</BarChart>*/}
                                    {/*</div>*/}
                                {/*))*/}
                            {/*}*/}
                        {/*</row>*/}
                    </div>
                </div>
            </div>
        )
    }
}

export default SurveyStats;