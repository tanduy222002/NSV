import { TbTransferVertical } from 'react-icons/tb';
import RowAction from './RowAction';

export enum ColumnType {
    Text = 'Text',
    Button = 'Button',
    Action = 'Action'
}

type Column = {
    title: string;
    sortable: boolean;
    type: 'Text' | 'Button' | 'Action';
};

type TableViewProps = {
    columns: Column[];
    items: object[];
    addAction?: () => void;
    editAction?: () => void;
    deleteAction?: () => void;
};

const TableView = ({
    columns,
    items,
    addAction,
    editAction,
    deleteAction
}: TableViewProps) => {
    return (
        <table className="w-full h-fit border-collapse">
            <tr className="border border-1">
                {columns.map(({ title, sortable }) => (
                    <th className="px-2 py-2" key={title}>
                        <div className="flex items-center gap-2 text-[#B5B7C0] text-base">
                            <p>{title}</p>
                            {sortable && <TbTransferVertical />}
                        </div>
                    </th>
                ))}
            </tr>
            {items.map((item, i) => {
                const itemValues = Object.values(item);
                return (
                    <tr key={i} className="border border-1">
                        {columns.map((column, i) => (
                            <td
                                className="px-2 py-2 text-base font-semibold"
                                key={i}
                            >
                                {column.type === ColumnType.Text ? (
                                    itemValues[i]
                                ) : (
                                    <RowAction
                                        addAction={addAction}
                                        editAction={editAction}
                                        deleteAction={deleteAction}
                                    />
                                )}
                            </td>
                        ))}
                    </tr>
                );
            })}
        </table>
    );
};

export default TableView;
