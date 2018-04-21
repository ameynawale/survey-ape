import React, { Component } from 'react';


class CheckBox extends Component {
    constructor(props) {
        super(props);
        this.state = {
            options: []
        };

        this.handleInputChange = this.handleInputChange.bind(this);
    }

    handleInputChange(event) {
        const target = event.target;
        const value = target.type === 'checkbox' ? target.checked : target.value;
        const name = target.name;

        this.setState({
            [name]: value
        });
    }

    addOption = (event) => {
        event.preventDefault();
        console.log(this.state.options);
        var options = this.state.options;
        options.push(
                <label>
                    <input type="radio" disabled="true"/>
                    <input
                        type="text"
                        onChange={this.handleInputChange} />
                    <br/>
                </label>
        )
        this.setState({
            options: options
        })
    }

    render() {
        return (
            <div>
                <p>{this.state.options}</p>
                <button className="btn btn-basic" onClick={this.addOption}>Add Option</button>
            </div>
        );
    }
}

export default CheckBox;