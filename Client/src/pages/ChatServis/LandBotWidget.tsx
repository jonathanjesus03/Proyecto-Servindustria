import { useEffect, useRef } from "react";

declare global {
  interface Window {
    Landbot: any;
  }
}

const LandbotContainer = () => {
  const containerRef = useRef<HTMLDivElement>(null);

  useEffect(() => {
    const script = document.createElement("script");
    script.type = "module";
    script.src = "https://cdn.landbot.io/landbot-3/landbot-3.0.0.mjs";
    script.async = true;

    script.onload = () => {
      if (window.Landbot && containerRef.current) {
        // Inicializa el Landbot dentro del contenedor referenciado
        new window.Landbot.Container({
          container: containerRef.current,
          configUrl:
            "https://storage.googleapis.com/landbot.online/v3/H-2971343-EB8CCU5RCBRJ2K7O/index.json",
        });
      }
    };

    document.body.appendChild(script);

    // Limpieza al desmontar
    return () => {
      // Remueve el iframe generado por Landbot dentro del contenedor
      if (containerRef.current) {
        containerRef.current.innerHTML = "";
      }
      document.body.removeChild(script);
    };
  }, []);

  return (
    <div
      id="myLandbot"
      ref={containerRef}
      style={{
        width: "100%",
        maxWidth: "600px",
        height: "500px",
        borderRadius: "16px",
        overflow: "hidden",
        boxShadow: "0 10px 25px rgba(0, 0, 0, 0.15)",
        background: "linear-gradient(to bottom, #ffcdcd, #fe7575)",
      }}
    ></div>
  );
};

export default LandbotContainer;
