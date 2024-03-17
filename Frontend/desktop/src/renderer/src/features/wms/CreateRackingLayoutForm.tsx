import { useState, useRef } from 'react';
import { useModal } from '@renderer/hooks';
import { FormSection, FormTitle, FormInput, RackLayout } from './components';
import { FaBraille } from 'react-icons/fa6';
import { CgCloseR } from 'react-icons/cg';
import { MdWarehouse } from 'react-icons/md';

import { Button } from '@renderer/components';
import { Rack } from './type';

const CreateRackingLayoutForm = () => {
    const { closeModal } = useModal();
    const nameRef = useRef<HTMLInputElement>(null);
    const areaRef = useRef<HTMLInputElement>(null);
    const slotRef = useRef<HTMLInputElement>(null);
    const [rackLayout, setRackLayout] = useState<Rack[][]>([]);

    const addRackLine = () => {
        console.log(
            'name: ',
            nameRef?.current?.value,
            'slot: ',
            slotRef?.current?.value
        );

        const slot = parseInt(slotRef!.current!.value);
        const name = nameRef?.current?.value;

        const newRackLine = Array.from(Array(slot).keys())
            .map((slotId) => slotId + 1)
            .map((slotId) => ({
                name: `#${name}${slotId}`
            }));

        setRackLayout((prev) => [...prev, newRackLine]);
    };

    const handleCreateLayoutSubmit = () => {
        closeModal && closeModal();
    };

    return (
        <div className="inset-0 absolute bg-[#C2CDDB] bg-opacity-60 flex items-center justify-center">
            <form
                className="w-4/5 relative flex flex-col justify-center border border-[#1A3389] rounded-md bg-white py-5 px-8"
                onSubmit={handleCreateLayoutSubmit}
            >
                <CgCloseR
                    onClick={closeModal}
                    className="absolute top-5 right-5 text-[#1C274C] cursor-pointer w-[20px] h-[20px]"
                />
                <FormTitle title="Tạo sơ đồ kho" />
                <FormSection
                    title="Tạo dãy kho"
                    icon={<FaBraille />}
                    layoutClassName="flex flex-col gap-2"
                >
                    <div className="flex items-center justify-between flex-wrap gap-4">
                        <FormInput name="Số lô chứa" ref={slotRef} />
                        <FormInput name="Diện tích(m²)" ref={areaRef} />
                        <FormInput name="Tên lô" ref={nameRef} />
                    </div>
                    <Button
                        text="Thêm mới"
                        className="text-[#1A3389] border-[#1A3389]"
                        action={addRackLine}
                    />
                </FormSection>
                <FormSection
                    title="Xem trước"
                    icon={<MdWarehouse />}
                    layoutClassName="flex flex-col items-center"
                >
                    <RackLayout rackLayout={rackLayout} />
                </FormSection>
                <Button
                    text="Lưu sơ đồ"
                    className="text-[#1A3389] border-[#1A3389] mx-auto"
                />
            </form>
        </div>
    );
};

export default CreateRackingLayoutForm;
