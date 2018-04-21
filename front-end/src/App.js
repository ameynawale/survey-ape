import React, { Component } from 'react';
import {BrowserRouter} from 'react-router-dom';
import CustomRouter from './components/CustomRouter';
import logo from './logo.svg';
import './App.css';

class App extends Component {
  render() {
    return (
      <div className="App">
        <BrowserRouter>
            <CustomRouter/>
        </BrowserRouter>
      </div>
    );
  }
}

export default App;
