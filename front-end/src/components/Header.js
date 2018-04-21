import React, { Component } from 'react';
import '../styles/Header.css';

class Header extends Component {
    render() {
        return (
            <div className="header-bar">
                <button className="btn btn-primary survey-button">Surveys</button>
                <button className="btn btn-primary share-button">Sign Out</button>
                <button className="btn btn-primary share-button">Share</button>
            </div>
        );
    }
}

export default Header;