import Button from '@material-ui/core/Button';
import TextField from '@material-ui/core/TextField';
import * as React from 'react';
import Request from '../../util/request';
import './create-board-form.css';

interface IProps {
    onSubmitted: () => void,
    isVisible: boolean;
}

interface IState {
    rows: number;
    cols: number;
}

export default class CreateBoardForm extends React.Component<IProps, IState> {
    public render() {
        const { isVisible } = this.props;

        return (
            <form className={isVisible ? 'create-board-form' : 'hide'} onSubmit={this.createBoard}>
                <TextField
                    required={true}
                    id="required"
                    label="Rows"
                    margin="normal"
                    onChange={this.handleRowsChange}
                />
                <TextField
                    required={true}
                    id="required"
                    label="Columns"
                    margin="normal"
                    onChange={this.handleColsChange}
                />
                <Button variant="outlined" color="default" size="small" type="submit" className='submit-btn'>
                    create board
                </Button>
            </form>
        );
    }

    private createBoard = (event: any) => {
        event.preventDefault();

        const request = new Request('http://localhost:8080/gameoflife/api/v1/board/create', 'POST', { rows: this.state.rows, cols: this.state.cols });

        request.execute().then(() => {
            this.props.onSubmitted();
        }).catch(err => {
            console.log(err);
        });
    }

    private handleRowsChange = (event: any) => {
        const rows = event.target.value;

        this.setState({ rows });
    }

    private handleColsChange = (event: any) => {
        const cols = event.target.value;

        this.setState({ cols });
    }
}