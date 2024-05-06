import { TbTransferVertical } from 'react-icons/tb';
import RowAction from './RowAction';

export enum ColumnType {
    Text = 'Text',
    Button = 'Button',
    Action = 'Action',
    Image = 'Image'
}

type Column = {
    title: string;
    sortable: boolean;
    type: ColumnType;
};
type TableViewProps = {
    columns: Column[];
    items: object[];
    viewAction?: (param: any) => void;
    editAction?: (param: any) => void;
    deleteAction?: () => void;
};

const TableView = ({
    columns,
    items,
    viewAction,
    editAction,
    deleteAction
}: TableViewProps) => {
    if (items.length === 0)
        return <h2 className="text-sm font-semibold">Chưa có dữ liệu </h2>;
    return (
        <table className="w-fit min-w-[750px] h-fit border-collapse">
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
                                ) : column.type === ColumnType.Image ? (
                                    <img src={itemValues[i]} />
                                ) : (
                                    <RowAction
                                        id={itemValues?.[0] ?? i}
                                        viewAction={viewAction}
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
