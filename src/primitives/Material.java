package primitives;

public class Material {
    private double _kD;
    private double _kS;
    private int _nShininess;
    private double _kR;
    private double _kT;
    private  double _rT;
    private double _rR;

    /* ********* Constructors ***********/
    /**
     *  a new Material
     *
     * @param kD Kd
     * @param kS Ks
     * @param nShininess Shininess
     * @param kR Kr
     * @param kT Kt
     */
    public Material(double kD, double kS, int nShininess, double kR, double kT ,double rR ,double rT) {
        _kD = kD;
        _kS = kS;
        _nShininess = nShininess;
        _kR = kR;
        _kT = kT;
        _rR=rR;
        _rT=rT;
    }

    /**
     *  a new Material
     *
     * @param kD Kd
     * @param kS Ks
     * @param nShininess Shininess
     * @param kR Kr
     * @param kT Kt
     */
    public Material(double kD, double kS, int nShininess, double kR, double kT) {
        _kD = kD;
        _kS = kS;
        _nShininess = nShininess;
        _kR = kR;
        _kT = kT;
        _rR=0;
        _rT=0;
    }

    /**
     *  a new Material (without kR and kT)
     *
     * @param kD Kd
     * @param kS Ks
     * @param nShininess Shininess
     */
    public Material(double kD, double kS, int nShininess) {
        _kD = kD;
        _kS = kS;
        _nShininess = nShininess;
        _kR = 0;
        _kT = 0;
        _rR=0;
        _rT=0;
    }

    /**
     * default constructor
     */
    public Material(){
        _kD = 0;
        _kS = 0;
        _nShininess = 0;
        _kR = 0;
        _kT = 0;
        _rR=0;
        _rT=0;
    }

    /**
     * copy constructor
     *
     * @param material material
     */
    public Material(Material material){
        _nShininess = material._nShininess;
        _kS = material._kS;
        _kD = material._kD;
        _kR = material._kR;
        _kT = material._kT;
        _rT = material._rT;
        _rR= material._rR;
    }

    /* ************* Getters/Setters *******/

    /**
     * get KD
     *
     * @return KD
     */
    public double getKD() {
        return _kD;
    }

    /**
     * get KS
     *
     * @return KS
     */
    public double getKS() {
        return _kS;
    }

    /**
     * get NShininess
     *
     * @return NShininess
     */
    public int getNShininess() {
        return _nShininess;
    }

    /**
     * get KR
     *
     * @return KR
     */
    public double getKR() {
        return _kR;
    }

    /**
     * get KT
     *
     * @return KT
     */
    public double getKT() {
        return _kT;
    }

    /**
     * get RT
     *
     * @return RT
     */
    public double getRT() {
        return _rT;
    }

    /**
     * get RR
     *
     * @return RR
     */
    public double getRR() {
        return _rR;
    }

    @Override
    public String toString() {
        return "M{" +
                "kD=" + _kD +
                ", kS=" + _kS +
                ", nSh=" + _nShininess +
                '}';
    }
}
