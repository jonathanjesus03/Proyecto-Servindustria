import { Link } from "react-router-dom";
import { Button } from "@material-tailwind/react";

type Props = {
  link: string;
  style: string;
  img: string;
};

const IconButton = ({ link, style, img }: Props) => {
  return (
    <div>
      <Link to={link}>
        <Button
          type="button"
          className={`${style}`}
          placeholder={undefined}
          onPointerEnterCapture={undefined}
          onPointerLeaveCapture={undefined}
        >
          <img src={img} alt="icon" />
        </Button>
      </Link>
    </div>
  );
};

export default IconButton;
