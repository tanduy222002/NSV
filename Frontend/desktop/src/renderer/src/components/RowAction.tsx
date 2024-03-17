import { useState } from 'react';
import { BsThreeDots } from 'react-icons/bs';
import { FaEdit } from 'react-icons/fa';
import { MdOutlineAddBox } from 'react-icons/md';
import { TiDeleteOutline } from 'react-icons/ti';

type RowActionProps = {
    addAction?: () => void;
    editAction?: () => void;
    deleteAction?: () => void;
};

const RowAction = ({ addAction, editAction, deleteAction }: RowActionProps) => {
    const [display, setDisplay] = useState(false);

    const toggleRowMenu = () => setDisplay((prev) => !prev);
    const hideRowMenu = () => setDisplay(false);
    return (
        <button
            className="relative rounded-full flex items-center justify-center w-[25px] h-[25px] cursor-pointer hover:bg-gray-200"
            onClick={toggleRowMenu}
            onBlur={hideRowMenu}
        >
            <BsThreeDots />
            {display && (
                <div className="absolute top-5 left-[40px] min-w-[120px] overflow-hidden rounded-md border bg-white border-gray-200">
                    <div
                        onClick={addAction}
                        className="flex items-center gap-2 cursor-pointer hover:bg-gray-100 px-2 py-1 text-[#00B087]"
                    >
                        <p>Thêm mới</p>
                        <MdOutlineAddBox />
                    </div>
                    <div
                        onClick={editAction}
                        className="flex items-center gap-2 cursor-pointer hover:bg-gray-100 px-2 py-1 text-[#F78F1E]"
                    >
                        <p>Chỉnh sửa</p>
                        <FaEdit />
                    </div>
                    <div
                        onClick={deleteAction}
                        className="flex items-center gap-2 cursor-pointer hover:bg-gray-100 px-2 py-1 text-[#DF0404]"
                    >
                        <p>Xóa</p>
                        <TiDeleteOutline />
                    </div>
                </div>
            )}
        </button>
    );
};

export default RowAction;
