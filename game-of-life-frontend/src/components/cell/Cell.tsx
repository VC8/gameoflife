import * as React from 'react';
import ICell from '../../model/cell/Cell';
import './cell.css';

interface IProps {
    cell: ICell;
}

export default class Cell extends React.Component<IProps, any> {
    public render(): JSX.Element {
        const { alive } = this.props.cell;

        return (
            <div className="cell">{alive ? 1 : 0}</div>
        );
    }
}