(function() {
    'use strict';
    angular
        .module('intelligentChartApp')
        .factory('MovieCategory', MovieCategory);

    MovieCategory.$inject = ['$resource'];

    function MovieCategory ($resource) {
        var resourceUrl =  'api/movie-categories/:id';

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
