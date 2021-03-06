(function() {
    'use strict';
    angular
        .module('intelligentChartApp')
        .factory('Chart', Chart);

    Chart.$inject = ['$resource'];

    function Chart ($resource) {
        var resourceUrl =  'api/charts/:id';

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
            'update': { method:'PUT' },
            'queryData': {
                method: 'GET',
                params: { id : '@id'},
                url: 'api/charts/:id/data'
            }
        });
    }
})();
