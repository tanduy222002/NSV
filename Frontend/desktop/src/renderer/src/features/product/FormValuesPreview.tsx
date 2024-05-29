import { useRef } from 'react';
import { IoMdAdd, IoMdClose } from 'react-icons/io';
import { FieldArray } from 'formik';
import { FormInput, Button, Wrapper } from '@renderer/components';

type FormValuePreviewProps = {
    name: string;
    label: string;
    values: string[];
};

const FormValuesPreview = ({ name, label, values }: FormValuePreviewProps) => {
    const inputRef = useRef<HTMLInputElement>(null);
    const addItem = (pushCallback, value: string | undefined) => {
        if (value != null && value.trim() !== '') {
            console.log('type:  ', typeof pushCallback);
            pushCallback(value);
            inputRef.current!.value = '';
        }
    };

    return (
        <div className="flex items-center gap-5">
            <FieldArray
                name={name}
                render={(arrayHelpers) => (
                    <div>
                        <Wrapper gap="md">
                            <FormInput
                                name={name}
                                label={label}
                                ref={inputRef}
                            />
                            <Button
                                icon={<IoMdAdd />}
                                className="text-[#1A3389] border-[#1A3389] hover:bg-indigo-200"
                                action={() =>
                                    addItem(
                                        arrayHelpers.push,
                                        inputRef.current?.value
                                    )
                                }
                            />
                        </Wrapper>
                        <div className="flex gap-5 flex-wrap">
                            {values.length > 0
                                ? values.map((value, i) => {
                                      return (
                                          <div
                                              key={i}
                                              className="flex items-center gap-2 px-2 py-1 rounded-md border text-indigo-500 border-indigo-500"
                                          >
                                              <p>{value}</p>
                                              <IoMdClose
                                                  className="cursor-pointer hover:text-red-500 translate-y-[1px]"
                                                  onClick={() =>
                                                      arrayHelpers.remove(
                                                          Number(i)
                                                      )
                                                  }
                                              />
                                          </div>
                                      );
                                  })
                                : null}
                        </div>
                    </div>
                )}
            />
        </div>
    );
};

export default FormValuesPreview;
