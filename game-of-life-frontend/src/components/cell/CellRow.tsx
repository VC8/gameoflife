import * as React from 'react';
import ICell from '../../model/cell/Cell';
import Cell from './Cell';
import './cellrow.css';

interface IProps {
    row: ICell[];
}

export default class CellRow extends React.Component<IProps, any> {
    public render(): JSX.Element {
        return (
            <> {this.renderCell()} </>
        );
    }

    private renderCell(): JSX.Element[] {
        const { row } = this.props;

        return row.map((cell: ICell, idx: number) => {

            return (
                <Cell cell={cell} key={idx} />
            );
        })
    }
}