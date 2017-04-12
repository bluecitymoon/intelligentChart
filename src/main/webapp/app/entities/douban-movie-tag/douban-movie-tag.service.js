(function() {
    'use strict';
    angular
        .module('intelligentChartApp')
        .factory('DoubanMovieTag', DoubanMovieTag);

    DoubanMovieTag.$inject = ['$resource'];

    function DoubanMovieTag ($resource) {
        var resourceUrl =  'api/douban-movie-tags/:id';

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
