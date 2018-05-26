import Button from '@material-ui/core/Button';
import * as React from 'react';
import GameBoard from '../../model/board/Board';
import Cell from '../../model/cell/Cell';
import Request from '../../util/request';
import CellRow from '../cell/CellRow';
import './board.css';
import CreateBoardForm from './create-board-form';
import CycleActions from './cycle-actions';

interface IState {
    board: GameBoard,
    generation?: number,
    isCreateBoardFormVisible: boolean
}


class Board extends React.Component<{}, IState> {

    constructor(props: {}) {
        super(props);

        const gameboard = new GameBoard();
        this.state = { board: gameboard, isCreateBoardFormVisible: false };
    }

    public render(): JSX.Element {
        const { isCreateBoardFormVisible, generation } = this.state;
        const isBoardVisible = this.state.board.getCells().length > 1;

        return (
            <div className="wrap">
                <div className="create-board-section">
                    <Button variant="outlined" onClick={this.toggleCreateBoardForm}>
                        {isCreateBoardFormVisible ? '-' : '+'}
                    </Button>
                    <CreateBoardForm onSubmitted={this.onSubmitted} isVisible={isCreateBoardFormVisible} />
                </div>
                <p className={isBoardVisible ? 'generation-section' : 'hide'} >Generation: {generation}</p>
                <div className="board">
                    {this.renderCellRows()}
                </div>
                <CycleActions isVisible={!isCreateBoardFormVisible && isBoardVisible} onCycleActionCompleted={this.updateState} />
            </div>
        );
    }

    private renderCellRows(): JSX.Element[] {
        const { board } = this.state;
        const cells = board.getCells();

        return cells.map((row: Cell[], index: number) => {
            return (
                <CellRow row={row} key={index} />
            )
        })
    }

    private onSubmitted = () => {
        this.toggleCreateBoardForm();
        this.updateState();
    }

    private toggleCreateBoardForm = () => {
        const isCreateBoardFormVisible = !this.state.isCreateBoardFormVisible;

        this.setState({ isCreateBoardFormVisible });
    }

    private updateState = () => {
        const request = new Request('http://localhost:8080/api/v1/gameoflife/board/state');

        const self = this;
        request.execute().then(response => {
            return response.json()
        }).then(data => {
            const { generation, cells } = data;
            const board = new GameBoard(cells);

            self.setState({ generation, board });
        }).catch(err => {
            console.log(err);
        });
    };

}

export default Board