import React, { Component } from 'react';
import PropTypes from 'prop-types';
import { Dropdown, DropdownToggle, DropdownMenu, DropdownItem } from 'reactstrap';
import '../styles/FormContent.css';
import '../styles/Header.css';
import '../styles/SingleSelect.css';


class QuestionDropDown extends Component {
    constructor(props) {
        super(props);
        this.state = {
            options: this.props.optionsValue,
            values:[],
            dropdownOpen: true
        };
    }

    componentWillMount(){
        let opt = this.props.optionsValue;
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

                {/*<Dropdown  >*/}
                    {/*<DropdownMenu>*/}
                    {/*{*/}
                        {/*this.state.values.map((opt, index) => {*/}
                            {/*<DropdownItem >{opt}</DropdownItem>*/}
                        {/*})*/}
                    {/*}*/}
                    {/*</DropdownMenu>*/}
                {/*</Dropdown>*/}


                {/*<select>
                    {
                        this.state.options.map((opt, index) => {
                            <option value="index">{opt}</option>
                        })
                    }
                </select>
*/}


            </div>
        );
    }
}

export default QuestionDropDown;