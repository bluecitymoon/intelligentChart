(function() {
    'use strict';
    angular
        .module('intelligentChartApp')
        .factory('WordCloud', WordCloud);

    WordCloud.$inject = ['$resource'];

    function WordCloud ($resource) {
        var resourceUrl =  'api/word-clouds/:id';

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
