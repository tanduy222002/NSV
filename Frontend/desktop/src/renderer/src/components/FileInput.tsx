import { MdOutlineFileUpload } from 'react-icons/md';
import { ReactNode } from 'react';

type FileInputProps = {
    fileSrc?: string | undefined | null;
    onChange: (fileSrc: string) => void;
    fallbackImage: ReactNode;
};

const FileInput = ({ fileSrc, onChange, fallbackImage }: FileInputProps) => {
    const handleFileChange = (e) => {
        const file = e.target.files[0];
        if (file != null) {
            const reader = new FileReader();
            reader.readAsDataURL(file);

            reader.onload = () => {
                onChange(reader.result as string);
            };
        }
    };

    return (
        <div className="mx-auto flex-col flex items-center">
            <div className="w-[200px] h-[200px] p-3 my-3 border border-gray-300 rounded-md mx-auto flex items-center justify-center">
                {fileSrc == null ? (
                    fallbackImage
                ) : (
                    <img
                        className="fit-cover w-full h-full"
                        src={fileSrc}
                        alt="preview-image"
                    />
                )}
            </div>
            <button
                className=" relative self-center mb-3 w-fit px-2 py-2 flex gap-2 items-center rounded-md border border-sky-800 text-sky-800 hover:bg-sky-50"
                type="button"
            >
                <input
                    data-testid="file-input"
                    type="file"
                    // accept="image/*"
                    className="absolute opacity-0 h-full w-full cursor-pointer"
                    onChange={handleFileChange}
                />
                <p className="font-semibold text-base">Thêm ảnh</p>
                <MdOutlineFileUpload className="w-[20px] h-[20px]" />
            </button>
        </div>
    );
};

export default FileInput;
