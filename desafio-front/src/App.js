import React, { Component } from 'react';
import './App.css';
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import ListAnimals from "./components/listAnimals/App";
import CreateAnimals from "./components/createAnimals/App";


class App extends Component {

  render() {

    return (
        <Router>
        <Routes>
          <Route path="/" element={<ListAnimals />} />
          <Route path="/create" element={<CreateAnimals />} />
        </Routes>
      </Router>
    );
  }
}

export default App;
