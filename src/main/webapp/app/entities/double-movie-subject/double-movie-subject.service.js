(function() {
    'use strict';
    angular
        .module('intelligentChartApp')
        .factory('DoubleMovieSubject', DoubleMovieSubject);

    DoubleMovieSubject.$inject = ['$resource'];

    function DoubleMovieSubject ($resource) {
        var resourceUrl =  'api/double-movie-subjects/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
