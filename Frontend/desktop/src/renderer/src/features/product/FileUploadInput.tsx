import { MdOutlineFileUpload } from 'react-icons/md';
import { GiFruitBowl } from 'react-icons/gi';

type FileInputProps = {
    fileSrc?: File | undefined | null;
    setFileSrc: (fileSrc: File) => void;
};

const FileUploadInput = ({ fileSrc, setFileSrc }: FileInputProps) => {
    const handleFileChange = (e) => {
        const file = e.target.files[0];
        if (file != null) {
            setFileSrc(file);
        }
    };
    console.log(fileSrc?.path);

    return (
        <div className="mx-auto">
            {fileSrc == null ? (
                <GiFruitBowl className="w-4/5 h-4/5 min-[400px]" />
            ) : (
                <div className="w-fit h-4/5 mx-auto">
                    <img
                        className="fit-cover"
                        src={fileSrc?.path}
                        alt="preview-image"
                    />
                </div>
            )}
            <button
                className=" relative self-center w-fit px-2 py-2 flex gap-2 items-center rounded-md border border-blue-500 text-blue-500 hover:bg-blue-50"
                type="button"
            >
                <input
                    type="file"
                    // accept="image/*"
                    className="absolute opacity-0 h-full w-full cursor-pointer"
                    onChange={handleFileChange}
                />
                <p className="font-semibold text-base">Thêm ảnh minh họa</p>
                <MdOutlineFileUpload className="w-[20px] h-[20px]" />
            </button>
        </div>
    );
};

export default FileUploadInput;
