(function() {
    'use strict';
    angular
        .module('intelligentChartApp')
        .factory('Website', Website);

    Website.$inject = ['$resource'];

    function Website ($resource) {
        var resourceUrl =  'api/websites/:id';

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
