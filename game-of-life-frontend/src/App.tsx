import * as React from 'react';
import './App.css';
import Board from './components/board/Board';

class App extends React.Component {
  public render() {
    return (
      <div className="App">
        <h1>The Game of Life</h1>
        <Board />
      </div>
    );
  }
}

export default App;
