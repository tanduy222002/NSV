import { render, fireEvent } from '@testing-library/react';
import Button from '../Button'; // Assuming Button component is in the same directory

// Mocking the cn function from '@renderer/utils/util'
jest.mock('@renderer/utils/util', () => ({
    cn: jest.fn((...args) => args.join(' '))
}));

describe('Button Component', () => {
    // Reset the mock implementation after each test
    afterEach(() => {
        jest.clearAllMocks();
    });

    it('renders button with provided text', () => {
        const { getByText } = render(
            <Button text="Click me" className="custom-class" />
        );
        expect(getByText('Click me')).toBeInTheDocument();
    });

    it('renders button with provided icon', () => {
        const { getByTestId } = render(
            <Button
                icon={<span data-testid="icon" />}
                className="custom-class"
            />
        );
        expect(getByTestId('icon')).toBeInTheDocument();
    });

    it('calls action function when button is clicked', () => {
        const onClick = jest.fn();
        const { getByText } = render(
            <Button text="Click me" action={onClick} className="" />
        );
        fireEvent.click(getByText('Click me'));
        expect(onClick).toHaveBeenCalled();
    });

    it('applies custom class along with default styles', () => {
        render(<Button text="Click me" className="custom-class" />);
        expect(
            document.querySelector(
                '.flex.items-center.justify-center.gap-2.px-2.py-1.border.rounded-md.font-semibold.w-fit.custom-class'
            )
        ).toBeInTheDocument();
    });

    it('applies default type if not provided', () => {
        const { container } = render(<Button text="Click me" className="" />);
        const button = container.querySelector('button');
        expect(button).toHaveAttribute('type', 'button');
    });

    it('applies provided type', () => {
        const { container } = render(
            <Button text="Submit" type="submit" className="" />
        );
        const button = container.querySelector('button');
        expect(button).toHaveAttribute('type', 'submit');
    });

    it('applies default className if not provided', () => {
        render(<Button text="Click me" className="" />);
        expect(
            document.querySelector(
                '.flex.items-center.justify-center.gap-2.px-2.py-1.border.rounded-md.font-semibold.w-fit'
            )
        ).toBeInTheDocument();
    });

    it('applies default className if className is empty', () => {
        render(<Button text="Click me" className="" />);
        expect(
            document.querySelector(
                '.flex.items-center.justify-center.gap-2.px-2.py-1.border.rounded-md.font-semibold.w-fit'
            )
        ).toBeInTheDocument();
    });

    it('calls cn function with correct arguments for className', () => {
        render(<Button text="Click me" className="custom-class" />);
        expect(require('@renderer/utils/util').cn).toHaveBeenCalledWith(
            'flex items-center justify-center gap-2 px-2 py-1 border rounded-md font-semibold w-fit',
            'custom-class'
        );
    });
});
