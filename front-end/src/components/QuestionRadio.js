import React, { Component } from 'react';
import PropTypes from 'prop-types';
import { Dropdown, DropdownToggle, DropdownMenu, DropdownItem } from 'reactstrap';
import '../styles/FormContent.css';
import '../styles/Header.css';
import '../styles/SingleSelect.css';
import { Button, Form, FormGroup, Label, Input, FormText } from 'reactstrap';

class QuestionRadio extends Component {
    constructor(props) {
        super(props);
        this.state = {
            options: this.props.radioValue,
            values:[]
        };
    }

    componentDidMount(){
        let opt = this.state.options;
        let items = [];
        for (let i = 0; i < opt.length; i++) {
            items.push(opt[i].option);

        }
        this.setState({
            ...this.state,
            values: items
        })
    }
    // getdropDownOptions(){
    //
    //     return items;
    // }

    render() {
        return (
            <div>
                        {
                            this.state.values.map((opt, index) => {
                                <input type="radio" value="option1" checked={true} />
                                {opt}
                            })
                        }

                <input
                    type="radio"
                    value="small"
                />
                Small
                <input
                    type="radio"
                    value="small"
                />
                Small
            </div>
        );
    }
}

export default QuestionRadio;