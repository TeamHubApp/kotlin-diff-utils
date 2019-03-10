import java.util.ArrayList;
import java.util.List;
    private static final String UNIFIED_DIFF_CHUNK_REGEXP = "^@@\\s+-(?:(\\d+)(?:,(\\d+))?)\\s+\\+(?:(\\d+)(?:,(\\d+))?)\\s+@@$";

    private final UnifiedDiffLine[] MAIN_PARSER_RULES = new UnifiedDiffLine[]{
        new UnifiedDiffLine(true, "^diff\\s", this::processDiff),
        new UnifiedDiffLine(true, "^index\\s[\\da-zA-Z]+\\.\\.[\\da-zA-Z]+(\\s(\\d+))?$", this::processIndex),
        new UnifiedDiffLine(true, "^---\\s", this::processFromFile),
        new UnifiedDiffLine(true, "^\\+\\+\\+\\s", this::processToFile),
        new UnifiedDiffLine(true, UNIFIED_DIFF_CHUNK_REGEXP, this::processChunk)

    private UnifiedDiff parse() throws IOException, UnifiedDiffParserException {
    public static UnifiedDiff parseUnifiedDiff(InputStream stream) throws IOException, UnifiedDiffParserException {
    private boolean processLine(boolean header, String line) throws UnifiedDiffParserException {
        for (UnifiedDiffLine rule : MAIN_PARSER_RULES) {
            data.addFile(actualFile);
    public void processDiff(MatchResult match, String line) {
        actualFile.setDiffCommand(line);
    }

    public void processChunk(MatchResult match, String chunkStart) {
        try {
            List<String> originalTxt = new ArrayList<>();
            List<String> revisedTxt = new ArrayList<>();

            int old_ln = match.group(1) == null ? 1 : Integer.parseInt(match.group(1));
            int new_ln = match.group(3) == null ? 1 : Integer.parseInt(match.group(3));

            while (this.READER.ready()) {
                String line = READER.readLine();

                if (line.startsWith(" ") || line.startsWith("+")) {
                    revisedTxt.add(line.substring(1));
                }
                if (line.startsWith(" ") || line.startsWith("-")) {
                    originalTxt.add(line.substring(1));
                }
                if (line.equals("")) {
                    break;
                }
            }

        } catch (IOException ex) {
            Logger.getLogger(UnifiedDiffParser.class.getName()).log(Level.SEVERE, null, ex);
            throw new UnifiedDiffParserException(ex);
        }
    public void processIndex(MatchResult match, String line) {
    private void processFromFile(MatchResult match, String line) {
        initFileIfNecessary();
        actualFile.setFromFile(extractFileName(line));
    }

    private void processToFile(MatchResult match, String line) {
        initFileIfNecessary();
        actualFile.setToFile(extractFileName(line));
    }

    private String extractFileName(String line) {
        return line.substring(4).replaceFirst("^(a|b)\\/", "");
    }

        public boolean processLine(String line) throws UnifiedDiffParserException {