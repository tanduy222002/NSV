import { MdKeyboardArrowDown } from 'react-icons/md';
import { useAppSelector } from '@renderer/hooks';
import { GoBell } from 'react-icons/go';

const UserInfo = () => {
    const user = useAppSelector((state) => state.auth.value);
    return (
        <div className="flex items-center gap-5 ml-auto w-fit">
            <GoBell className="w-[20px] h-[20px] cursor-pointer" />
            <div className="flex items-center gap-1 border border-sky-700 rounded-md font-semibold px-2 py-2 text-sky-700">
                <p>{user?.username}</p>
                <MdKeyboardArrowDown className="w-[20px] h-[20px] cursor-pointer text-sky-700" />
            </div>
        </div>
    );
};

export default UserInfo;
