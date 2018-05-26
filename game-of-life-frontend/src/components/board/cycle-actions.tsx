import Button from '@material-ui/core/Button';
import AddIcon from '@material-ui/icons/Add';
import RemoveIcon from '@material-ui/icons/Remove';
import * as React from 'react';
import Request from '../../util/request';
import './cycle-actions.css';

interface IProps {
    isVisible: boolean;
    onCycleActionCompleted: () => void
}

export default class CycleActions extends React.Component<IProps, {}> {
    public render(): JSX.Element {
        const { isVisible } = this.props;

        return (
            <div className={isVisible ? 'cycle-actions' : 'hide'}>
                <p className="action-label increment-label">Inrement</p>
                <Button variant="fab" color="primary" aria-label="add" className="btn increment-cycle-btn" onClick={this.incrementCycle}>
                    <AddIcon />
                </Button>
                <Button variant="fab" mini={true} color="secondary" aria-label="remove" className="btn derement-cycle-btn" onClick={this.decrementCycle}>
                    <RemoveIcon />
                </Button>
                <p className="action-label decrement-label">Decrement</p>
            </div>
        );
    }

    private incrementCycle = () => {
        const request = new Request('http://localhost:8080/api/v1/gameoflife/board/increment', 'POST');

        const { onCycleActionCompleted } = this.props;
        request.execute().then(response => {
            onCycleActionCompleted();
        }).catch(err => {
            console.log(err);
        });
    }

    private decrementCycle = () => {
        const request = new Request('http://localhost:8080/api/v1/gameoflife/board/decrement', 'POST');

        const { onCycleActionCompleted } = this.props;
        request.execute().then(response => {
            onCycleActionCompleted();
        }).catch(err => {
            console.log(err);
        });
    }
}