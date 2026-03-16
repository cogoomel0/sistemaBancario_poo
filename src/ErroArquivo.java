class ErroArquivo extends Exception {
    public ErroArquivo(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
