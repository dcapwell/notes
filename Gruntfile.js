var path = require("path");

module.exports = function (grunt) {
    grunt.loadNpmTasks('grunt-gitbook');
    grunt.loadNpmTasks('grunt-gh-pages');
    grunt.loadNpmTasks('grunt-contrib-clean');

    grunt.initConfig({
        'gitbook': {
            development: {
                input: "./site",
                format: "site",
                title: "Notes",
                description: "My developer notes",
                github: "dcapwell/notes"
            }
        },
        'gh-pages': {
            options: {
                base: '_book'
            },
            src: ['**']
        },
        'clean': {
            files: ['.grunt', '_book']
        }
    });

    grunt.registerTask('publish', [
        'clean',
        'gitbook',
        'gh-pages'
    ]);
    grunt.registerTask('default', 'gitbook');
};
