import { useState } from 'react';
import { BsThreeDots } from 'react-icons/bs';
import { FaEdit } from 'react-icons/fa';
import { CgDetailsMore } from 'react-icons/cg';
import { TiDeleteOutline } from 'react-icons/ti';

type RowActionProps = {
    id: string | number;
    viewAction?: (param: string | number) => void;
    editAction?: (param: string | number) => void;
    deleteAction?: (param: string | number) => void;
};

const RowAction = ({
    id,
    viewAction,
    editAction,
    deleteAction
}: RowActionProps) => {
    const [display, setDisplay] = useState(false);

    const toggleRowMenu = () => setDisplay((prev) => !prev);
    const hideRowMenu = () => setDisplay(false);
    return (
        <button
            data-testid="menu-control"
            className="relative rounded-full flex items-center justify-center w-[25px] h-[25px] cursor-pointer hover:bg-gray-200"
            onClick={toggleRowMenu}
            onBlur={hideRowMenu}
        >
            <BsThreeDots />
            {display && (
                <div className="absolute top-5 left-[40px] min-w-[120px] overflow-hidden rounded-md border bg-white border-gray-200">
                    <div
                        data-testid="view-btn"
                        onClick={() => viewAction && viewAction(id)}
                        className="flex items-center gap-2 cursor-pointer hover:bg-gray-100 px-2 py-1 text-[#00B087]"
                    >
                        <CgDetailsMore />
                        <p>Chi tiết</p>
                    </div>
                    <div
                        data-testid="edit-btn"
                        onClick={() => editAction && editAction(id)}
                        className="flex items-center gap-2 cursor-pointer hover:bg-gray-100 px-2 py-1 text-[#F78F1E]"
                    >
                        <FaEdit />
                        <p>Chỉnh sửa</p>
                    </div>
                    <div
                        data-testid="delete-btn"
                        onClick={() => deleteAction && deleteAction(id)}
                        className="flex items-center gap-2 cursor-pointer hover:bg-gray-100 px-2 py-1 text-[#DF0404]"
                    >
                        <TiDeleteOutline />
                        <p>Xóa</p>
                    </div>
                </div>
            )}
        </button>
    );
};

export default RowAction;
