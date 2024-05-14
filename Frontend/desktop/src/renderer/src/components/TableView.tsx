import { TbTransferVertical } from 'react-icons/tb';
import RowAction from './RowAction';

export enum ColumnType {
    Text = 'Text',
    Link = 'Link',
    Action = 'Action',
    Image = 'Image'
}

type Column = {
    title: string;
    sortable: boolean;
    type: ColumnType;
};

type TableItem = any & {
    viewAction?: () => void;
};

type TableViewProps = {
    columns: Column[];
    items: TableItem[];
    viewAction?: (param: number | string) => void;
    editAction?: (param: number | string) => void;
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
        <table className="table-auto min-w-[720px] h-fit border-collapse">
            <tr className="border border-bottom-1">
                {columns.map(({ title, sortable }) => (
                    <th className="px-2 py-2" key={title}>
                        <div className="flex items-center gap-2 text-base text-sky-800">
                            <p>{title}</p>
                            {sortable && <TbTransferVertical />}
                        </div>
                    </th>
                ))}
            </tr>
            {items.map((item, i) => {
                const itemValues = Object.values(item);
                const [, itemId] = Object.entries(item).filter(
                    ([key]) => key.toLowerCase() === 'id'
                )[0];
                return (
                    <tr key={i} className="border border-1">
                        {columns.map((column, i) => (
                            <td
                                className="px-2 py-2 text-base font-medium"
                                key={i}
                            >
                                {column.type === ColumnType.Text ? (
                                    (itemValues[i] as string)
                                ) : column.type === ColumnType.Image ? (
                                    <div className="w-[40px] h-[40px] mx-auto ">
                                        <img
                                            src={itemValues[i] as any}
                                            className="object-cover"
                                        />
                                    </div>
                                ) : column.type === ColumnType.Action ? (
                                    <div className="mx-auto w-fit">
                                        <RowAction
                                            id={itemId as string}
                                            viewAction={
                                                item.viewAction ?? viewAction
                                            }
                                            editAction={editAction}
                                            deleteAction={deleteAction}
                                        />
                                    </div>
                                ) : (
                                    <div className="flex justify-center">
                                        {item?.icon}
                                    </div>
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
